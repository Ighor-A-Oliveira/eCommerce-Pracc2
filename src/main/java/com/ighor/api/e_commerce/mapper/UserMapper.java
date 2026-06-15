package com.ighor.api.e_commerce.mapper;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.model.entity.Address;
import com.ighor.api.e_commerce.model.entity.Order;
import com.ighor.api.e_commerce.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDTO userParaDTO(User user){
        UserDTO dto = new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getCreatedAt(),
                user.getCart() != null ? user.getCart().getId() : null,
                // Se a lista de endereços não for nula, transforma em IDs. Se for nula, retorna lista vazia.
                user.getAddresses() != null ? user.getAddresses().stream().map(Address::getId).toList() : new ArrayList<>(),
                // Se a lista de pedidos não for nula, transforma em IDs. Se for nula, retorna lista vazia.
                user.getOrders() != null ? user.getOrders().stream().map(Order::getId).toList() : new ArrayList<>()
        );

        return dto;
    }

    public List<UserDTO> usersParaDTO(List<User> users){
        if (users == null) {
            return new ArrayList<>();
        }
        List<UserDTO> dtos = new ArrayList<>();

        //for each user de users
        for (User user : users) {

            //Extraindo ID do Cart
            Long cartId = null;
            //Se nao estiver nulo
            if (user.getCart() != null) {
                //Add ao DTO desse user
                cartId = user.getCart().getId();
            }

            //Extraindo ID do Address
            List<Long> addressIds = new ArrayList<>();
            //Se nao estiver nulo
            if (user.getAddresses() != null) {
                //for each address de user.getAddresses()
                for (Address addr : user.getAddresses()) {
                    //Add ao DTO desse user
                    addressIds.add(addr.getId());
                }
            }

            //Extraindo ID do Pedido
            List<Long> orderIds = new ArrayList<>();
            //Se nao estiver nulo
            if (user.getOrders() != null) {
                //for each order de user.getOrders()
                for (Order order : user.getOrders()) {
                    //Add ao DTO desse user
                    orderIds.add(order.getId());
                }
            }

            //Criando DTO do user
            UserDTO dto = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRole(),
                    user.getCreatedAt(),
                    cartId,
                    addressIds,
                    orderIds
            );

            //Add DTO a lista
            dtos.add(dto);
        }


        return dtos;
    }
}
