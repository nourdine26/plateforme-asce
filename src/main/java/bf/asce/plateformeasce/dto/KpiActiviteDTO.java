package bf.asce.plateformeasce.dto;

public class KpiActiviteDTO {
    private Float tauxAvancement;
    private Float tauxRetard;
    private Float tauxRespectDelais;
    private String statut;

    public KpiActiviteDTO() {}

    public KpiActiviteDTO(Float tauxAvancement, Float tauxRetard, Float tauxRespectDelais, String statut) {
        this.tauxAvancement = tauxAvancement;
        this.tauxRetard = tauxRetard;
        this.tauxRespectDelais = tauxRespectDelais;
        this.statut = statut;
    }

    public Float getTauxAvancement() { return tauxAvancement; }
    public void setTauxAvancement(Float tauxAvancement) { this.tauxAvancement = tauxAvancement; }
    public Float getTauxRetard() { return tauxRetard; }
    public void setTauxRetard(Float tauxRetard) { this.tauxRetard = tauxRetard; }
    public Float getTauxRespectDelais() { return tauxRespectDelais; }
    public void setTauxRespectDelais(Float tauxRespectDelais) { this.tauxRespectDelais = tauxRespectDelais; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
