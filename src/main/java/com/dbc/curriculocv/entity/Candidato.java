package com.dbc.curriculocv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CANDIDATO")
@AllArgsConstructor
@NoArgsConstructor
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CANDIDATO_SEQUENCIA")
    @SequenceGenerator(name = "CANDIDATO_SEQUENCIA", sequenceName = "seq_candidato", allocationSize = 1)
    @Column(name = "id_candidato")
    private Integer idCandidato;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "senioridade")
    private String senioridade;

    @JsonIgnore
    @OneToMany(mappedBy = "candidato")
    private Set<DadosEscolares> dadosEscolares;

    @JsonIgnore
    @OneToMany(mappedBy = "candidato")
    private Set<Experiencias> experiencias;

    @JsonIgnore
    @ManyToMany(mappedBy = "candidatos")
    private Set<Vaga> vagas;

    @JsonIgnore
    @OneToOne(mappedBy = "candidato", cascade = CascadeType.REMOVE)
    private Curriculo curriculo;

}