package com.mysema.query.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.junit.Test;

public class InterfaceType2Test {

    public interface IEntity {
        
        Long getId();
    }
    
    public interface User extends IEntity {        
        
        Party getParty();
        
        String getUsername();
        
    }
    
    public interface Party extends IEntity {
        
        String getName();
    }


    @MappedSuperclass
    public class EntityImpl {
        
        @Id
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        
    }
        
    @Entity
    @Table(name = "USERS")
    @org.hibernate.annotations.AccessType("field")
    @org.hibernate.annotations.Proxy(proxyClass = User.class)
    public class UserImpl extends EntityImpl implements User {

        @NaturalId(mutable = true)
        @Column(name = "USERNAME", nullable = false)
        private String username;

        @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, targetEntity = PartyImpl.class)
        @JoinColumn(name = "PARTY_ID", nullable = false)
        private Party party;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Party getParty() {
            return party;
        }

        public void setParty(Party party) {
            this.party = party;
        }
        
    }

    @javax.persistence.Entity
    @Table(name = "PARTY")
    @org.hibernate.annotations.AccessType("field")
    @org.hibernate.annotations.Proxy(proxyClass = Party.class)
    public abstract class PartyImpl extends EntityImpl implements Party {

        @Column(name = "NAME", nullable = false)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
    }
    
    @Test
    public void User_party_Is_Party() {
        assertEquals(
                QInterfaceType2Test_Party.class,
                QInterfaceType2Test_User.user.party.getClass());
    }
    
    @Test
    public void User_id__Is_Available() {
        assertNotNull(QInterfaceType2Test_User.user); // FIXME
    }

    
}
