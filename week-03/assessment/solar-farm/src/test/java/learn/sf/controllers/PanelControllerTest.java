package learn.sf.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.sf.data.PanelRepository;
import learn.sf.domain.PanelService;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PanelControllerTest {

    @MockBean
    PanelRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception {

        List<Panel> panels = List.of(
            new Panel(1, "Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true),
            new Panel(2, "Jazz",54,22,1983,PanelMaterial.AMORPHOUS_SILICON,false),
            new Panel(3, "Gospel",13,15,2005,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true),
            new Panel(4, "Jazz",54,10,2016,PanelMaterial.AMORPHOUS_SILICON,false)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(panels);

        when(repository.findAll()).thenReturn(panels);

        mvc.perform(get("/panels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Panel panelIn = new Panel("Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        Panel expected = new Panel(1, "Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        when(repository.add(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddEmptyName() throws Exception {
        Panel panelIn = new Panel("",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddNullName() throws Exception {
        Panel panelIn = new Panel(null,3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddInvalidRow() throws Exception {
        Panel panelIn = new Panel("Bluegrass",-13,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddInvalidColumn() throws Exception {
        Panel panelIn = new Panel("Bluegrass",15,4999,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddInvalidYear() throws Exception {
        Panel panelIn = new Panel("Bluegrass",15,87,1000, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddNullMaterial() throws Exception {
        Panel panelIn = new Panel("Bluegrass",15,87,1999, null,true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = post("/panels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        Panel panelIn = new Panel(1, "Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        when(repository.update(panelIn)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = put("/panels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldNotUpdateNull() throws Exception {
        Panel panelIn = null;

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(panelIn);

        var request = put("/panels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteById() throws Exception {
        when(repository.findById(1)).thenReturn(new Panel(1, "Bluegrass",3,15,1994, PanelMaterial.MULTICRYSTALLINE_SILICON,true));
        when(repository.deleteById(1)).thenReturn(true);

        var request = delete("/panels/1");

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldNotDeleteByInvalidId() throws Exception {
        when(repository.findById(100000)).thenReturn(null);
        when(repository.deleteById(100000)).thenReturn(true);

        var request = delete("/panels/100000");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}