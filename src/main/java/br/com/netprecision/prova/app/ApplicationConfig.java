package br.com.netprecision.prova.app;

import br.com.netprecision.prova.provider.AppJacksonJsonProvider;
import br.com.netprecision.prova.resource.PedidoResource;
import br.com.netprecision.prova.resource.ProdutoResource;
import br.com.netprecision.prova.resource.RegistroPontoResource;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new java.util.HashSet<>();

        System.out.println("REST configuration starting: getClasses()");

        resources.add(JacksonFeature.class);
        resources.add(AppJacksonJsonProvider.class);
        resources.add(PedidoResource.class);
        resources.add(ProdutoResource.class);
        resources.add(RegistroPontoResource.class);

        System.out.println("REST configuration ended successfully.");

        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.wadl.disableWadl", true);
        return properties;
    }
}
