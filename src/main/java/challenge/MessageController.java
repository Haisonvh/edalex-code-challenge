package challenge;

import challenge.database.MessageModel;
import challenge.database.MessageModelAssembler;
import challenge.database.MessageModelRepository;
import challenge.exception.MessageNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageModelRepository modelRepository;  
    
    @Autowired
    private MessageModelAssembler modelAssembler;   

    
    @GetMapping("/messages")
    public CollectionModel<EntityModel<MessageModel>> all() {

        List<EntityModel<MessageModel>> messages = modelRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(message -> modelAssembler.toModel(message))
                .collect(Collectors.toList());

        return new CollectionModel<>(messages, linkTo(methodOn(MessageController.class).all()).withSelfRel());

    }

    @PostMapping("/messages")
    public MessageModel newMessage(@RequestBody MessageModel newMessage) {
        return modelRepository.save(newMessage);
    }

    @GetMapping("/messages/{id}")
    public EntityModel<MessageModel> one(@PathVariable Long id) {
        MessageModel message = modelRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));        
        return modelAssembler.toModel(message);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        modelRepository.deleteById(id);
    }
}
