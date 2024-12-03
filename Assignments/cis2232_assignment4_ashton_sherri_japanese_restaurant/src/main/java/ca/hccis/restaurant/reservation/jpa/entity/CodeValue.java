/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.hccis.restaurant.reservation.jpa.entity;

//import jakarta.persistence.Basic;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.TemporalType;
//import org.hibernate.annotations.Table;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author bjmaclean
 */
@Entity
@Table(name = "codevalue")
public class CodeValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codeTypeId")
    private int codeTypeId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codeValueSequence")
    private Integer codeValueSequence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "englishDescription")
    private String englishDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "englishDescriptionShort")
    private String englishDescriptionShort;
    @Size(max = 100)
    @Column(name = "frenchDescription")
    private String frenchDescription;
    @Size(max = 20)
    @Column(name = "frenchDescriptionShort")
    private String frenchDescriptionShort;
    @Column(name = "sortOrder")
    private Integer sortOrder;
    @Column(name = "createdDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDateTime;
    @Size(max = 20)
    @Column(name = "createdUserId")
    private String createdUserId;
    @Column(name = "updatedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;
    @Size(max = 20)
    @Column(name = "updatedUserId")
    private String updatedUserId;

    public CodeValue() {
    }

    public CodeValue(Integer codeValueSequence) {
        this.codeValueSequence = codeValueSequence;
    }

    public CodeValue(Integer codeValueSequence, int codeTypeId, String englishDescription, String englishDescriptionShort) {
        this.codeValueSequence = codeValueSequence;
        this.codeTypeId = codeTypeId;
        this.englishDescription = englishDescription;
        this.englishDescriptionShort = englishDescriptionShort;
    }

    public int getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(int codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public Integer getCodeValueSequence() {
        return codeValueSequence;
    }

    public void setCodeValueSequence(Integer codeValueSequence) {
        this.codeValueSequence = codeValueSequence;
    }

    public String getEnglishDescription() {
        return englishDescription;
    }

    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
    }

    public String getEnglishDescriptionShort() {
        return englishDescriptionShort;
    }

    public void setEnglishDescriptionShort(String englishDescriptionShort) {
        this.englishDescriptionShort = englishDescriptionShort;
    }

    public String getFrenchDescription() {
        return frenchDescription;
    }

    public void setFrenchDescription(String frenchDescription) {
        this.frenchDescription = frenchDescription;
    }

    public String getFrenchDescriptionShort() {
        return frenchDescriptionShort;
    }

    public void setFrenchDescriptionShort(String frenchDescriptionShort) {
        this.frenchDescriptionShort = frenchDescriptionShort;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeValueSequence != null ? codeValueSequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object instanceof CodeValue) {
            CodeValue other = (CodeValue) object;
            return (this.codeValueSequence != null || other.codeValueSequence == null) && (this.codeValueSequence == null || this.codeValueSequence.equals(other.codeValueSequence));
        }
        return false;
    }

    @Override
    public String toString() {
        return "entity.jpa.ca.hccis.restaurant.reservation.CodeValue[ codeValueSequence=" + codeValueSequence + " ]";
    }
    
}
