package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.dolgov.ntcbserver.clienthandler.ClientHandler;
import ru.dolgov.ntcbserver.clienthandler.ClientHandlerImpl;

public class MessageHandler extends ChannelInboundHandlerAdapter{
	private final int PREAMBULA_LENGTH = 4;
	private final int PREFIX_LENGTH = 6;
	private final int PREFIX_POSITION = 16;
	
	private byte[] bytesFromRequest;
	private byte[] bytesToResponse;
	private ClientHandler clientHandler;
	private long currentConnectedImei;

	public MessageHandler() {
		clientHandler = new ClientHandlerImpl();
		bytesFromRequest = new byte[0];
		bytesToResponse = new byte[0];
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		
		getBytesFromRequest(message);
		
		if (validateRequest()) {

			getDataFromRequestAndCreateResponse();

			clientHandler.saveClientStatus(clientHandler.getClientByImei(currentConnectedImei));

			sendResponse(ctx);
		}
	}
	
	private void getBytesFromRequest(Object message) {
		ByteBuf buffer = (ByteBuf) message;
		bytesFromRequest = new byte[buffer.readableBytes()];
		buffer.readBytes(bytesFromRequest);
	}
	
	private boolean validateRequest() {
		if (isMessageToConnect() || isMessageToSetting()) {
			byte csd = getCrc(Arrays.copyOfRange(bytesFromRequest, 16, bytesFromRequest.length));
			byte csp = getCrc(Arrays.copyOfRange(bytesFromRequest, 0, 15));
			if ((csd == bytesFromRequest[14]) && (csp == bytesFromRequest[15])) {
				return true;
			}
		}
		if (isMessageWithStatus()) {
			byte crc = getCrc8(Arrays.copyOfRange(bytesFromRequest, 0, bytesFromRequest.length - 1));
			if (crc == bytesFromRequest[bytesFromRequest.length-1]) {
				return true;
			}
		}
		return false;
	}
	
	private void getDataFromRequestAndCreateResponse() {
		if (isMessageToConnect()) {
			getImeiFromRequestToConnect();
			if (validateImei()) {
				getDataFromRequestToConnect();
				createResponseToConnect();
			}
		}
		if (isMessageToSetting()) {
			getDataFromRequestToSettings();
			createResponseToSettings();
		}
		if (isMessageWithStatus()) {
			getAndSaveDataFromRequestWithStatus();
			createResponseToStatus();
		}
	}

	private void getImeiFromRequestToConnect() {
		currentConnectedImei = getImeiFromBytes(Arrays.copyOfRange(bytesFromRequest, 20, 35));
	}

	private boolean validateImei() {
		if (clientHandler.getClientByImei(currentConnectedImei) != null) {
			return true;
		}
		return false;
	}

	private void getDataFromRequestToConnect() {
		clientHandler.getClientByImei(currentConnectedImei).setIdDc(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 4, 8)));
		clientHandler.getClientByImei(currentConnectedImei).setIdObj(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 8, 12)));
		clientHandler.getClientByImei(currentConnectedImei).setSize(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 12, 14)));
		clientHandler.getClientByImei(currentConnectedImei).setImei(currentConnectedImei);
	}
	
	private void getDataFromRequestToSettings() {
		clientHandler.getClientByImei(currentConnectedImei).setProtocol(bytesFromRequest[22]);
		clientHandler.getClientByImei(currentConnectedImei).setProtocolVersion(bytesFromRequest[23]);
		clientHandler.getClientByImei(currentConnectedImei).setStructureVersion(bytesFromRequest[24]);
		clientHandler.getClientByImei(currentConnectedImei).setDataSize(bytesFromRequest[25]);
		clientHandler.getClientByImei(currentConnectedImei).setSettings(getBooleanArrayFromBytes(Arrays.copyOfRange(bytesFromRequest, 26, 35)));
	}
	
	private void getAndSaveDataFromRequestWithStatus() {
		clientHandler.getClientByImei(currentConnectedImei)
				.setNumberOfPage(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 2, 6)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setCode(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 6, 8)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setTime(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 8, 12)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setStage(bytesFromRequest[12] & 0xFF);
		clientHandler.getClientByImei(currentConnectedImei)
				.setLastTime(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 13, 17)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setLantitude(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 17, 21)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setLontitude(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 21, 25)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setSpeed(getFloatFromBytes(Arrays.copyOfRange(bytesFromRequest, 25, 29)));
		clientHandler.getClientByImei(currentConnectedImei)
				.setCourse(getIntFromBytes(Arrays.copyOfRange(bytesFromRequest, 29, 31)));

		clientHandler.saveClientStatus(clientHandler.getClientByImei(currentConnectedImei));
	}
	
	private boolean isMessageToConnect() {
		String preambula = getPreambulaFromBytes();
		String prefix = getPrefixFromBytes();
		if ((preambula.startsWith("@NTC")) && (prefix.startsWith("*>S:"))) {
			return true;
		}
		return false;
	}
	
	private boolean isMessageToSetting() {
		String preambula = getPreambulaFromBytes();
		String prefix = getPrefixFromBytes();
		if ((preambula.startsWith("@NTC")) && (prefix.startsWith("*>FLEX"))) {
			return true;
		}
		return false;
	}
	
	private boolean isMessageWithStatus() {
		String preambula = getPreambulaFromBytes();
		if (preambula.startsWith("~C")) {
			return true;
		}
		return false;
	}

	private void createResponseToConnect() {
		bytesToResponse = new byte[19];
		bytesToResponse[0] = 0x40;
		bytesToResponse[1] = 0x4e;
		bytesToResponse[2] = 0x54;
		bytesToResponse[3] = 0x43;
        byte[] tmp = getBytesFromInt(clientHandler.getClientByImei(currentConnectedImei).getIdObj());
        for (int i = 0; i < tmp.length; i++) {
        	bytesToResponse[i + 4] = tmp[tmp.length - 1 - i];
        }
        tmp = getBytesFromInt(clientHandler.getClientByImei(currentConnectedImei).getIdDc());
        for (int i = 0; i < tmp.length; i++) {
        	bytesToResponse[i + 8] = tmp[tmp.length - 1 - i];
        }
        bytesToResponse[12] = 0x03;
        bytesToResponse[13] = 0x00;
        bytesToResponse[16] = 0x2a;
        bytesToResponse[17] = 0x3c;
        bytesToResponse[18] = 0x53;
        bytesToResponse[14] = getCrc(Arrays.copyOfRange(bytesToResponse, 16, 19));
        bytesToResponse[15] = getCrc(Arrays.copyOfRange(bytesToResponse, 0, 15));
	}
	
	private void createResponseToSettings() {
		bytesToResponse = new byte[25];
		bytesToResponse[0] = 0x40;
		bytesToResponse[1] = 0x4e;
		bytesToResponse[2] = 0x54;
		bytesToResponse[3] = 0x43;
        byte[] tmp = getBytesFromInt(clientHandler.getClientByImei(currentConnectedImei).getIdObj());
        for (int i = 0; i < tmp.length; i++) {
        	bytesToResponse[i + 4] = tmp[tmp.length - 1 - i];
        }
        tmp = getBytesFromInt(clientHandler.getClientByImei(currentConnectedImei).getIdDc());
        for (int i = 0; i < tmp.length; i++) {
        	bytesToResponse[i + 8] = tmp[tmp.length - 1 - i];
        }
        bytesToResponse[12] = 0x09;
        bytesToResponse[13] = 0x00;
        bytesToResponse[16] = 0x2a;// *
        bytesToResponse[17] = 0x3c;// >
        bytesToResponse[18] = 0x46;// F
        bytesToResponse[19] = 0x4c;// L
        bytesToResponse[20] = 0x45;// E
        bytesToResponse[21] = 0x58;// X
        bytesToResponse[22] = Byte.
				parseByte(Integer.toString(clientHandler.getClientByImei(currentConnectedImei).getProtocol()));
        bytesToResponse[23] = Byte.
				parseByte(Integer.toString(clientHandler.getClientByImei(currentConnectedImei).getProtocolVersion()));
        bytesToResponse[24] = Byte.
				parseByte(Integer.toString(clientHandler.getClientByImei(currentConnectedImei).getStructureVersion()));
        bytesToResponse[14] = getCrc(Arrays.copyOfRange(bytesToResponse, 16, 25));
        bytesToResponse[15] = getCrc(Arrays.copyOfRange(bytesToResponse, 0, 15));
	}
	
	private void createResponseToStatus() {
		bytesToResponse = new byte[3];
		bytesToResponse[0] = 0x7e;
		bytesToResponse[1] = 0x43;
		bytesToResponse[2] = getCrc8(Arrays.copyOfRange(bytesToResponse, 0, 2));
	}
	
	private void sendResponse(ChannelHandlerContext ctx) {
		ByteBuf answer = ctx.alloc().buffer(bytesToResponse.length);
		answer.writeBytes(bytesToResponse);
		ctx.write(answer);
		ctx.flush();
	}
	
	private String getPreambulaFromBytes() {
		char[] charsToPreambula = new char[PREAMBULA_LENGTH];
		for (int i = 0; i < PREAMBULA_LENGTH; i++) {
			charsToPreambula[i] = (char) bytesFromRequest[i];
		}
		return new String(charsToPreambula);
	}

	private String getPrefixFromBytes() {
		char[] charsToPrefix = new char[PREFIX_LENGTH];
		for (int i = 0; i < PREFIX_LENGTH; i++) {
			charsToPrefix[i] = (char) bytesFromRequest[i + PREFIX_POSITION];
		}
		return new String(charsToPrefix);
	}
	
	private byte getCrc(byte[] bytes) {
        int summ = 0;
        for (int i = 0; i < bytes.length; i++) {
            summ ^= bytes[i];
        }
        return (byte)(summ & 0xFF);
    }
	
	private byte getCrc8 (byte[] buffer)
    {
        byte crc = (byte) 0xFF;
        for (byte b : buffer) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                crc = (crc & 0x80) != 0 ? (byte) ((crc << 1) ^ 0x31) : (byte) (crc << 1);
            }
        }
        return crc;
    }
	
	private int getIntFromBytes(byte[] bytes) {
        int result = bytes[0] & 0xFF;
        for (int i = bytes.length - 1; i > 0; i--) {
            result |= ((bytes[i] & 0xFF) << (i * 8));
        }
        return result;
    }
	
	private long getLongFromBytes(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    private long getImeiFromBytes(byte[] bytes) {
		char[] chars = new char[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			chars[i] = (char)bytes[i];
		}
		String str = new String(chars);
		return Long.parseLong(str);
	}

    private float getFloatFromBytes(byte[] bytes) {
		return Float.intBitsToFloat(getIntFromBytes(bytes));
	}

	private boolean[] getBooleanArrayFromBytes(byte[] bytes) {
		boolean[] bits = new boolean[bytes.length * 8];
	    for (int i = 0; i < bytes.length * 8; i++) {
	      if ((bytes[i / 8] & (1 << (7 - (i % 8)))) > 0)
	        bits[i] = true;
	    }
	    return bits;
	}
	
	private byte[] getBytesFromInt(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((value >> 24) & 0xFF);
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
	}
}