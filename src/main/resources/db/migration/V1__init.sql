CREATE TABLE empresa (
  id SERIAL NOT NULL,
  cnpj varchar(255) NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  razao_social varchar(255) NOT NULL
);

CREATE TABLE funcionario (
  id SERIAL NOT NULL,
  cpf varchar(255) NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  email varchar(255) NOT NULL,
  nome varchar(255) NOT NULL,
  perfil varchar(255) NOT NULL,
  qtd_horas_almoco float DEFAULT NULL,
  qtd_horas_trabalho_dia float DEFAULT NULL,
  senha varchar(255) NOT NULL,
  valor_hora decimal(19,2) DEFAULT NULL,
  empresa_id bigint DEFAULT NULL
);

CREATE TABLE lancamento (
  id SERIAL NOT NULL,
  data TIMESTAMP NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  localizacao varchar(255) DEFAULT NULL,
  tipo varchar(255) NOT NULL,
  funcionario_id bigint DEFAULT NULL
);

--
-- Indexes for table empresa
--
ALTER TABLE empresa
  ADD PRIMARY KEY (id);

--
-- Indexes for table funcionario
--
ALTER TABLE funcionario
  ADD PRIMARY KEY (id);

--
-- Indexes for table lancamento
--
ALTER TABLE lancamento
  ADD PRIMARY KEY (id);

--
-- Constraints for dumped tables
--

--
-- Constraints for table funcionario
--
ALTER TABLE funcionario
  ADD CONSTRAINT empresa_fk FOREIGN KEY (empresa_id) REFERENCES empresa (id);

--
-- Constraints for table lancamento
--
ALTER TABLE lancamento
  ADD CONSTRAINT funcionario_fk FOREIGN KEY (funcionario_id) REFERENCES funcionario (id);