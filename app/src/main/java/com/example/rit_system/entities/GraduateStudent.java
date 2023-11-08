package com.example.rit_system.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe que representa um estudante de pós-graduação, que é uma extensão da
 * classe Student.
 */
public class GraduateStudent extends Student implements Serializable {
    private String graduateProgram; // Programa de pós-graduação do estudante.
    private String researchTitle; // Título da pesquisa do estudante.
    private LocalDate defenseDate; // Data da defesa da pesquisa do estudante.

    /**
     * Define a data da defesa da pesquisa do estudante.
     *
     * @param defenseDate A data da defesa da pesquisa.
     */
    public void setDefenseDate(LocalDate defenseDate) {
        this.defenseDate = defenseDate;
    }

    /**
     * Define o programa de pós-graduação do estudante.
     *
     * @param graduateProgram O programa de pós-graduação.
     */
    public void setGraduateProgram(String graduateProgram) {
        this.graduateProgram = graduateProgram;
    }

    /**
     * Define o título da pesquisa do estudante.
     *
     * @param researchTitle O título da pesquisa.
     */
    public void setResearchTitle(String researchTitle) {
        this.researchTitle = researchTitle;
    }

    /**
     * Obtém a data da defesa da pesquisa do estudante.
     *
     * @return A data da defesa da pesquisa.
     */
    public LocalDate getDefenseDate() {
        return defenseDate;
    }

    /**
     * Obtém o programa de pós-graduação do estudante.
     *
     * @return O programa de pós-graduação.
     */
    public String getGraduateProgram() {
        return graduateProgram;
    }

    /**
     * Obtém o título da pesquisa do estudante.
     *
     * @return O título da pesquisa.
     */
    public String getResearchTitle() {
        return researchTitle;
    }

    /**
     * Retorna uma representação em string do objeto GraduateStudent.
     *
     * @return Uma representação em string do objeto.
     */
    @Override
    public String toString() {
        return "GraduateStudent{" +
                "posgraduateProgram='" + graduateProgram + '\'' +
                ", researchTitle='" + researchTitle + '\'' +
                ", defenseDate=" + defenseDate +
                "} " + super.toString();
    }
}