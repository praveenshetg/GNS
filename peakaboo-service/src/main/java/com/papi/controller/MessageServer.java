package com.papi.controller;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/message")
public class MessageServer {
	@OnMessage
	  public String echoText(String name) {
	    return name;
	  }
}
