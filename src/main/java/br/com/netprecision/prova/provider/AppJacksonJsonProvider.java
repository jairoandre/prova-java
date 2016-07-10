package br.com.netprecision.prova.provider;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import br.com.netprecision.prova.serializer.PedidoSerializer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Provider
public class AppJacksonJsonProvider implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(Include.NON_EMPTY);
        MAPPER.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
        SimpleModule mod = new SimpleModule("Pedido module");
        mod.addSerializer(new PedidoSerializer());
        MAPPER.registerModule(mod);
    }

    public AppJacksonJsonProvider() {
        System.out.println("Instantiate AppJacksonJsonProvider");
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        System.out.println("AppJacksonJsonProvider.getContext() called with type: " + type);
        return MAPPER;
    }
}
