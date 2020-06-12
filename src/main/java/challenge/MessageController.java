package challenge;

import challenge.database.MessageModel;
import challenge.database.MessageModelAssembler;
import challenge.database.MessageModelRepository;
import challenge.exception.MessageNotFoundException;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageModelRepository modelRepository;  
    
    @Autowired
    private MessageModelAssembler modelAssembler;   

    /**
     * Get all messages and sort base on the id (DESC)
     * @return list of message
     */
    @GetMapping("/messages")
    public CollectionModel<EntityModel<MessageModel>> all() {

        List<EntityModel<MessageModel>> messages = modelRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(message -> modelAssembler.toModel(message))
                .collect(Collectors.toList());

        return new CollectionModel<>(messages, linkTo(methodOn(MessageController.class).all()).withSelfRel());

    }

    /**
     * insert new message into database
     * @param newMessage should not be null, empty
     * @return the successfully added message
     */
    @PostMapping("/messages")
    public MessageModel newMessage(@Valid @RequestBody MessageModel newMessage) {
        return modelRepository.save(newMessage);
    }

    /**
     * get message based on id
     * @param id
     * @return message
     * @throws MessageNotFoundException when id is not found
     */     
    @GetMapping("/messages/{id}") 
    public EntityModel<MessageModel> one(@PathVariable Long id) throws MessageNotFoundException {
        MessageModel message = modelRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));        
        return modelAssembler.toModel(message);
    }

    /**
     * delete message based on id
     * @param id message need to be deleted
     * @throws MessageNotFoundException when id is not found
     */
    @DeleteMapping("/messages/{id}")
    public void deleteEmployee(@PathVariable Long id) throws MessageNotFoundException {
        MessageModel message = modelRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));         
        modelRepository.delete(message);
    }
    
    /**
     * Mapping error message from validation
     * @param ex
     * @return error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
