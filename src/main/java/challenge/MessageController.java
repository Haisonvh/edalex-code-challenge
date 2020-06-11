package challenge;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController("/api/message")
public class MessageController {
    @RequestMapping(method = RequestMethod.GET)
    List<MessageModel> listMessages() {
        // TODO - This is just a place holder, we expect to see this replaced using your service/persistence layer
        List<MessageModel> messages = new ArrayList<>();
        messages.add(new MessageModel(1, "First Message"));
        messages.add(new MessageModel(2, "Second Message"));
        messages.add(new MessageModel(3, "Third Message"));
        // end

        return messages;
    }
}
