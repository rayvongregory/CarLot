package com.genspark.CarLot.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserPfp")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPfp {
    @Id
    @Column(name = "PublicId")
    private String publicId;

    @Column(name = "UserId")
    private String userId;

    @Column(name = "URL")
    private String url;

    @Column(name = "AssetId")
    private String assetId;
}
