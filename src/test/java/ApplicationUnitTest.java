
import challenge.Application;
import challenge.database.MessageModel;
import challenge.database.MessageModelRepository;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author HaiSonVH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationUnitTest {

    @Autowired
    private MessageModelRepository messageModelRepository;
    
    
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUpData(){
        messageModelRepository.save(new MessageModel("one"));
        messageModelRepository.save(new MessageModel("two"));
    }
    
    @After
    public void teatDown(){
        messageModelRepository.deleteAll();
        //messageModelRepository.
    }
    @Test
    public void shouldReturn2SampleData()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/messages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].message", Is.is("two")))
                .andExpect(jsonPath("$.content[1].message", Is.is("one")));
    }
    
    @Test
    public void shouldReturnCorrectRecord()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/messages/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Is.is("one")));
    }
    
    @Test
    public void shouldReturnNotFoundWhenFindIncorrectRecord()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/messages/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find message 3"));
    }
    
    @Test
    public void shouldReturnNotBadRequestWhenAddBlankMessage()
            throws Exception {
        String mess = "{\"message\": \"\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .content(mess)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Is.is("Message must have length from 1 to 250")));
    } 
    
    @Test
    public void shouldReturnNotBadRequestWhenAddNullMessage()
            throws Exception {
        String mess = "{\"message\": null}";
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .content(mess)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Is.is("Message must have length from 1 to 250")));
    }  
    
    @Test
    public void shouldReturnNotBadRequestWhenAddMessageHasSpecialCharacter()
            throws Exception {
        String mess = "{\"message\": \"{}#\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .content(mess)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Is.is("Message should not contain special character")));
    } 
    
    
    @Test
    public void shouldReturnNotBadRequestWhenAddMessageHasOver250Characters()
            throws Exception {
        String mess = "{\"message\": \"shouldReturnNotBadRequestWhenAddMessageHasOver250CharactersshouldReturnNotBadRequestWhenAddMessageHasOver250CharactersshouldReturnNotBadRequestWhenAddMessageHasOver250CharactersshouldReturnNotBadRequestWhenAddMessageHasOver250CharactersshouldReturnNotBadRequestWhenAddMessageHasOver250Characters\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .content(mess)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Is.is("Message must have length from 1 to 250")));
    } 
    
    @Test
    public void shouldReturnOkWhenAddMessageSuccessfully()
            throws Exception {
        String mess = "{\"message\": \"three\"}";
        mvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .content(mess)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Is.is("three")))
                .andExpect(jsonPath("$.id", Is.is(3)));
    } 
    
    @Test
    public void shouldReturnOkWhenDeleteRecordSuccessully()
            throws Exception {
        
        mvc.perform(MockMvcRequestBuilders.delete("/api/messages/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        long count = messageModelRepository.count();
        Assert.assertEquals(1,count);
    }
    
    @Test
    public void shouldReturnNotFoundWhenDeleteIncorrectRecord()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/messages/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find message 3"));
    }
}
