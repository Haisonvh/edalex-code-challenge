/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.exception;

/**
 *
 * @author HaiSonVH
 */
public class MessageNotFoundException extends RuntimeException {

  public MessageNotFoundException(Long id) {
    super("Could not find message " + id);
  }
}
