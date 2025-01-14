package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Init;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.specimpl.BuiltResponse;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
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
    @EJB
    protected PackageBean packageBean;

    public static final String PACKAGE_ASSOCIATION_SUCCESSFUL = "Package association successful";
    @Override
    protected AbstractBean<OrderEntity> getBean() {
        return orderBean;
    }

    @Override
    protected OrderEntity convertToEntity(OrderDTO dto) {
        return new OrderEntity(dto.getCode(), dto.isReturned(), null, null, null);
    }

    @Override
    protected OrderDTO convertToDto(OrderEntity entity) {
        return new OrderDTO(entity.getId(), entity.getCode(), entity.isReturned(), null, null);
    }

    @Override
    protected void copyDtoToEntity(OrderDTO dto, OrderEntity entity) {
        entity.setCode(dto.getCode());
        entity.setReturned(dto.isReturned());
    }

    @PATCH
    @RolesAllowed({"Consumer", "Operator"})
    public Response associateOuterPackage(OrderDTO packageDTO) {
        Long orderID = packageDTO.getId();
        Long outerPackageId = packageDTO.getOuterOrderId();
        String result = packageBean.associateOuterPackage(orderID, outerPackageId);

        // Based on the result string, determine the response
        if (result.equals(PACKAGE_ASSOCIATION_SUCCESSFUL)) {
            return Response.ok(result).build();
        } else {
            // Assuming all other cases are error cases
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
    }

    @Override
    public Response find(Long id) {
        return super.find(id);
    }

    @Override
    public Response findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed({"Consumer", "Operator"})
    public Response create(OrderDTO orderDTO) {
        Response response;
        if(orderDTO.getCode() == null || orderDTO.getCode().isEmpty()){
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity("No code for entity ")
                    .build();
        }

        try {
             response = super.create(orderDTO);
            if(orderDTO.getQuantityByPackageID() != null) {
                orderBean.associatePackagesToOrder(((OrderDTO)(response).getEntity()).getId(), orderDTO.getQuantityByPackageID());
            }
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
        return response;
    }

    @Override
    @RolesAllowed({"Consumer", "Operator"})
    public Response update(Long id, OrderDTO orderDTO) {
        return super.update(id, orderDTO);
    }

    @Override
    @RolesAllowed({"Consumer", "Operator"})
    public Response delete(Long id) {
        return super.delete(id);
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
    @Path("{orderId}/package/{transportId}")
    @RolesAllowed({"Consumer", "Operator"})
    public Response associateTransportPackage(@PathParam("orderId") Long orderId, @PathParam("transportId") Long transportId) {
        try {
            orderBean.associateTransportPackage(orderId, transportId);
            return Response.ok().entity("Transport package associated successfully").build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error associating transport package: " + e.getMessage()).build();
        }
    }

}
