package com.example.singujismapa.Helper;

public class Soal {

    private String id_soal;
    private String id_paket;
    private String id_jenis_soal;
    private String pertanyaan;
    private String opsi_a;
    private String opsi_b;
    private String opsi_c;
    private String opsi_d;
    private String opsi_e;
    private String kunci_jawaban;
    private String pembahasan;


    public Soal()
    {

    }

    public String getId_soal() {
        return id_soal;
    }

    public void setId_soal(String id_soal) {
        this.id_soal = id_soal;
    }

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    public String getId_jenis_soal() {
        return id_jenis_soal;
    }

    public void setId_jenis_soal(String id_jenis_soal) {
        this.id_jenis_soal = id_jenis_soal;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getOpsi_a() {
        return opsi_a;
    }

    public void setOpsi_a(String opsi_a) {
        this.opsi_a = opsi_a;
    }

    public String getOpsi_b() {
        return opsi_b;
    }

    public void setOpsi_b(String opsi_b) {
        this.opsi_b = opsi_b;
    }

    public String getOpsi_c() {
        return opsi_c;
    }

    public void setOpsi_c(String opsi_c) {
        this.opsi_c = opsi_c;
    }

    public String getOpsi_d() {
        return opsi_d;
    }

    public void setOpsi_d(String opsi_d) {
        this.opsi_d = opsi_d;
    }

    public String getOpsi_e() {
        return opsi_e;
    }

    public void setOpsi_e(String opsi_e) {
        this.opsi_e = opsi_e;
    }

    public String getKunci_jawaban() {
        return kunci_jawaban;
    }

    public void setKunci_jawaban(String kunci_jawaban) {
        this.kunci_jawaban = kunci_jawaban;
    }

    public String getPembahasan() {
        return pembahasan;
    }

    public void setPembahasan(String pembahasan) {
        this.pembahasan = pembahasan;
    }
}
