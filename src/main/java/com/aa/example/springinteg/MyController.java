package com.aa.example.springinteg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private MessageChannel inputChannel;

    @Autowired
    private QueueChannel outputChannel;

	@GetMapping("/process")
	public String process(@RequestParam String payload) {
		System.out.println("process rest method: "+ payload);
		DataClass obj = new DataClass();
		obj.setId(101);
		obj.setName(payload);
		inputChannel.send(new GenericMessage<>(obj));
		
        // Receive the message from the output channel with a timeout (no need if no response is expected)
        Message<?> message = outputChannel.receive(5000);  // timeout in milliseconds
        if (message != null) {
        	System.out.println("Response is: "+message.getPayload());
            return (String) message.getPayload();
        } else {
            return "No response received";
        }
	}

}
