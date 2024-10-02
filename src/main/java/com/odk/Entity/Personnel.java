package com.odk.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Personnel extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "entite_id")
    private EntiteOdc entiteOdc;
}
