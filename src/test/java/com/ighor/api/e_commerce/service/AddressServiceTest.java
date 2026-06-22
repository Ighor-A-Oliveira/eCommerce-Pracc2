package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.AddressDTO;
import com.ighor.api.e_commerce.exception.ResourceNotFoundException;
import com.ighor.api.e_commerce.mapper.AddressMapper;
import com.ighor.api.e_commerce.model.entity.Address;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.AddressRepo;
import com.ighor.api.e_commerce.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    User user;
    Address addr;
    List<Address> addrs;
    AddressDTO request;
    Long id;
    List<AddressDTO> dtos;

    @Mock
    private AddressRepo addrRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private AddressMapper addrMapper;


    @InjectMocks
    private AddressService addrServ;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setCreatedAt(LocalDateTime.now());
        user.setName("user");
        user.setEmail("user@email.com");
        user.setPassword("123");
        user.setPhone("123");
        user.setRole(Role.CUSTOMER);

        addr = new Address(
                1L,
                "Rua Usuario",
                "11",
                "",
                "Vila do Usuario",
                "Sao Paulo",
                "Sao Paulo",
                "000100-000",
                true,
                user
        );

        addrs = new ArrayList<>();
        addrs.add(addr);

        request = new AddressDTO(
                1L,
                "Rua Usuario",
                "11",
                "",
                "Vila Usuario",
                "Sao Paulo",
                "Sao Paulo",
                "000100-000",
                true,
                1L
        );

        dtos = new ArrayList<>();
        dtos.add(request);

        id = 1L;
    }

    //Flow comum

    @Test
    @DisplayName("Deve criar um endereco")
    void criarEndereco(){
        //setup
        when(userRepo.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(addrRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        //criando o endereco
        addrServ.criarEndereco(request);

        //capturando o objeto
        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);

        //Checando se o objeto foi salvo
        verify(addrRepo).save(captor.capture());

        //Pegando o acesso ao objeto especifico
        Address saved = captor.getValue();

        //Usando para comparacao
        assertEquals("Rua Usuario", saved.getStreet());
        assertEquals("11", saved.getNumber());
        assertEquals(user, saved.getUser());
        assertTrue(saved.getIsDefault());

    }


    @Test
    @DisplayName("Deve fazer uma busca e retornar um AddressDTO")
    void buscarEnderecoPorId(){
        //setando o comportamento dos metodos
        when(addrRepo.findById(1L)).thenReturn(Optional.ofNullable(addr));
        when(addrMapper.addressParaDTO(addr)).thenReturn(request);

        //salvando
        AddressDTO resultado = addrServ.buscarEnderecoPorId(1L);

        //Verifica se o Service chamou addrRepo.findById(1L)
        verify(addrRepo).findById(1L);
        //Verifica se o Service chamou addrMapper.addressParaDTO(addr)
        verify(addrMapper).addressParaDTO(addr);

        //Verifica se os dois objetos sao iguais
        assertEquals(request, resultado);

    }


    @Test
    @DisplayName("Deve fazer uma busca e retornar uma lista de AddressDTO")
    void buscarTodosEnderecos() {
        //setando os comportamentos
        when(addrRepo.findAll()).thenReturn(addrs);
        when(addrMapper.addressesParaDTO(addrs)).thenReturn(dtos);

        //Capturando o resultado do metodo sendo testado
        List<AddressDTO> resultado = addrServ.buscarTodosEnderecos();

        //Checando se os metodos foram chamados
        verify(addrRepo).findAll();
        verify(addrMapper).addressesParaDTO(addrs);

        //Checando se o resultado eh igual ao esperado
        assertEquals(dtos, resultado);
    }


    @Test
    @DisplayName("Deve fazer uma uma atualizacao num registro de Address")
    void atualizarEnderecoPorId(){
        //request com dados diferentes
        AddressDTO request2 = new AddressDTO(
                2L,
                "Rua 3",
                "21",
                "",
                "Vila 3",
                "Sao Paulo",
                "Sao Paulo",
                "000102-000",
                true,
                1L
        );

        //definindo comportamento
        when(addrRepo.findById(1L)).thenReturn(Optional.ofNullable(addr));

        //chamando metodo sendo testado
        addrServ.atualizarEnderecoPorId(id, request2);

        //capturando objeto
        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        //checando se foi salvo
        verify(addrRepo).save(captor.capture());


        //Pegando o acesso ao objeto especifico
        Address saved = captor.getValue();

        //Comparando os dados da requisicao original com os dados atualizados
        assertNotEquals("Rua Usuario", saved.getStreet());
        assertNotEquals("11", saved.getNumber());
        assertEquals(user, saved.getUser());
        assertTrue(saved.getIsDefault());
        //Checando se os dois objetos sao diferentes
        assertNotEquals(saved, request);

    }

    @Test
    void deletarEnderecoPorId(){
        when(addrRepo.existsById(id)).thenReturn(true);
        addrServ.deletarEnderecoPorId(id);
        verify(addrRepo).deleteById(id);
    }

    //??????????????????????????????????????????????????????????????????????????????
    //Flow com exception

    @Test
    @DisplayName("Deve acionar a ResourceNotFoundException")
    void criarEnderecoResourceNotFoundException(){
        //qualquer id que o metodo receber vai retornar um optional vazio
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        //checando se a exception eh acionada
        assertThrows(ResourceNotFoundException.class,
                () -> addrServ.criarEndereco(request));

    }



    @Test
    @DisplayName("Deve acionar a ResourceNotFoundException")
    void buscarEnderecoPorIdResourceNotFoundException(){
        when(addrRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> addrServ.buscarEnderecoPorId(id));

    }


    @Test
    @DisplayName("Deve acionar a ResourceNotFoundException")
    void buscarTodosEnderecosResourceNotFoundException() {
        //setando os comportamentos
        when(addrRepo.findAll()).thenReturn(List.of());

        //when(addrRepo.findAll()).thenReturn(addrs);
        //when(addrMapper.addressesParaDTO(addrs)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> addrServ.buscarTodosEnderecos());
    }


    @Test
    @DisplayName("Deve fazer uma uma atualizacao num registro de Address")
    void atualizarEnderecoPorIdException(){
        //definindo comportamento
        when(addrRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> addrServ.atualizarEnderecoPorId(3L, request));

    }

    @Test
    void deletarEnderecoPorIdResourceNotFoundException(){
        //definindo oq o metodo deve retornar
        when(addrRepo.existsById(anyLong())).thenReturn(false);

        //Checando se a exception eh acionada
        assertThrows(ResourceNotFoundException.class,
                () -> addrServ.deletarEnderecoPorId(3L));

    }
}