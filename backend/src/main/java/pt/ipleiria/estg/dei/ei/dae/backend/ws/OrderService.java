package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderService extends AbstractService<OrderEntity, OrderDTO> {
    @EJB
    protected OrderBean orderBean;

    @Override
    protected AbstractBean<OrderEntity> getBean() {
        return orderBean;
    }

    @Override
    protected OrderEntity convertToEntity(OrderDTO dto) {
        return null;
    }

    @Override
    protected OrderDTO convertToDto(OrderEntity entity) {
        return new OrderDTO(entity.getCode());
    }

    @Override
    protected void copyDtoToEntity(OrderDTO dto, OrderEntity entity) {

    }

}
