package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Init;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class OrderService extends AbstractService<OrderEntity, OrderDTO> {
    @EJB
    protected OrderBean orderBean;

    @Override
    protected AbstractBean<OrderEntity> getBean() {
        return orderBean;
    }

    @Override
    protected OrderEntity convertToEntity(OrderDTO dto) {
        return new OrderEntity(dto.getCode(), dto.isReturned(), null);
    }

    @Override
    protected OrderDTO convertToDto(OrderEntity entity) {
        return new OrderDTO(entity.getId(), entity.getCode(), entity.isReturned(), null);
    }

    @Override
    protected void copyDtoToEntity(OrderDTO dto, OrderEntity entity) {
        entity.setCode(dto.getCode());
        entity.setReturned(dto.isReturned());
    }


    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response create(OrderDTO orderDTO) {
        Response response;
        try {
             response = super.create(orderDTO);
            if(orderDTO.getQuantityByPackageID() != null) {
                orderBean.associatePackagesToOrder(orderDTO.getId(), orderDTO.getQuantityByPackageID());
            }
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
        return response;
    }

    @Path("{id}/nopackage")
    public Response findNotAssociatedPackages(Long orderId) {
        Response response;
        try {
            List<PackageEntity> notAssociatedPackages = orderBean.findNotAssociatedPackages(orderId);
            response = Response.status(Response.Status.OK)
                    .entity(notAssociatedPackages)
                    .build();
        }catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
        return response;
    }

    @POST
    @Path("{orderId}/packages")
    @RolesAllowed({"Manufacturer", "Operator"})
    public void associatePackagesToOrder(@PathParam("orderId") Long orderId, OrderDTO orderDTO){
        orderBean.associatePackagesToOrder(orderId, orderDTO.getQuantityByPackageID());
    }
}
