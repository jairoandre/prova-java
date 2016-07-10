package br.com.netprecision.prova.serializer;

import br.com.netprecision.prova.model.Pedido;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by jairo on 09/07/2016.
 */
public class PedidoSerializer extends StdSerializer<Pedido> {

    public PedidoSerializer() {
        super(Pedido.class);
    }

    @Override
    public void serialize(Pedido pedido, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeObjectField("id", pedido.getId());
        jGen.writeBooleanField("fechado", pedido.getFechado());
        jGen.writeObjectField("itens", pedido.getItens().size());
        jGen.writeEndObject();
    }
}
