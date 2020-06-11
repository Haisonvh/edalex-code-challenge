/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.database;

import challenge.MessageController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

/**
 *
 * @author HaiSonVH
 */
@Component
public class MessageModelAssembler implements RepresentationModelAssembler<MessageModel, EntityModel<MessageModel>> {

  @Override
  public EntityModel<MessageModel> toModel(MessageModel message) {

    return new EntityModel<>(message,
                linkTo(methodOn(MessageController.class).one(message.getId())).withSelfRel(),
                linkTo(methodOn(MessageController.class).all()).withRel("messages"));
  }
}
