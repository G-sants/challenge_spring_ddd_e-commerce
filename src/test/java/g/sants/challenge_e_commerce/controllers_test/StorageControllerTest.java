package g.sants.challenge_e_commerce.controllers_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.port.input.StorageController;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.application.service.TokenService;
import g.sants.challenge_e_commerce.domain.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = StorageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private StorageRepository storageRepository;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    List<ItemDTOResponse> itemList;
    ItemDTOResponse itemDTOResponse;
    ItemDTORequest itemDTORequest;
    Storage storage;

    @BeforeEach
    void initItems() {
        storage = new Storage("Potato", 0.99,100);
        storage.setId(1L);

        itemDTORequest = new ItemDTORequest("Potato", 1.10,100);
        itemDTOResponse = new ItemDTOResponse(0.99,"Potato",100);

        ItemDTOResponse itemDTOResponse1 = new ItemDTOResponse(0.99,"Potato",
                100);
        ItemDTOResponse itemDTOResponse2 = new ItemDTOResponse(1.99,"Apple",
                100);

        itemList = new ArrayList<>();
        itemList.add(itemDTOResponse1);
        itemList.add(itemDTOResponse2);
    }

    @Test
    void StorageController_CreatesItem() throws Exception {
        given(storageService.createItem(itemDTORequest)).willReturn(storage);

        ResultActions response = mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storage)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void StorageController_GetsItemById() throws Exception {
        given(storageService.getItem(ArgumentMatchers.any())).willReturn(itemDTOResponse);

        ResultActions response = mockMvc.perform(get("/item/{item_id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDTOResponse)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void StorageController_GetAllItems() throws Exception {
        given(storageService.getAllItems()).willReturn(itemList);

        ResultActions response = mockMvc.perform(get("/item")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void StorageController_UpdatesItems() throws Exception {
        given(storageService.updateItem(1L,itemDTORequest)).willReturn(storage);

        ResultActions response = mockMvc.perform(put("/item/{item_id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDTORequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void StorageController_DeletesItem() throws Exception {
        doNothing().when(storageService).deleteItem(ArgumentMatchers.anyLong());

        ResultActions response = mockMvc.perform(delete("/item/{item_id}",1L)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
