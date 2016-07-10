CREATE TABLE public.funcionario
(
  id bigint NOT NULL,
  nm_nome character varying(100),
  CONSTRAINT "FUNCIONARIO_PK" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.funcionario
OWNER TO postgres;

CREATE TABLE public.item_pedido
(
  id bigint NOT NULL,
  id_pedido bigint,
  id_produto bigint,
  vl_quantidade integer,
  CONSTRAINT "ITEM_PEDIDO_PK" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.item_pedido
OWNER TO postgres;

CREATE TABLE public.pedido
(
  id bigint NOT NULL,
  fechado boolean DEFAULT false,
  CONSTRAINT "PEDIDO_PK" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.pedido
OWNER TO postgres;

CREATE TABLE public.produto
(
  id bigint NOT NULL,
  cd_codigo character varying(25),
  nm_nome character varying(50),
  vl_preco numeric(19,2),
  CONSTRAINT "PRODUTO_PK" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.produto
OWNER TO postgres;

CREATE TABLE public.registro_ponto
(
  id bigint NOT NULL,
  id_funcionario bigint,
  dt_data timestamp with time zone,
  cd_tipo integer,
  CONSTRAINT "REGISTRO_PONTO_PK" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.registro_ponto
OWNER TO postgres;



CREATE SEQUENCE public.seq_item_pedido
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_item_pedido
  OWNER TO postgres;

CREATE SEQUENCE public.seq_pedido
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.seq_pedido
OWNER TO postgres;

CREATE SEQUENCE public.seq_registro_ponto
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.seq_registro_ponto
OWNER TO postgres;
