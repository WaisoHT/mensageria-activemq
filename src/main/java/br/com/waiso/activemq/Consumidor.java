package br.com.waiso.activemq;

import java.util.Hashtable;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Consumidor {
	public static void main(String[] args) throws Exception {
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		env.put(Context.PROVIDER_URL, "tcp://Fabiano.local:61616");
		env.put("queue.minhaFila", "aaaa");

		Context context = new InitialContext(env);
		
		
//		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("minhaFila");
		MessageConsumer consumer = session.createConsumer(fila);
		Message mensagem = consumer.receive(50000);
		System.out.println(mensagem);
		
		new Scanner(System.in).nextLine();
		
		connection.close();
		context.close();
	}
}
