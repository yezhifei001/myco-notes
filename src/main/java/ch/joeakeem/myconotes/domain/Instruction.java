package ch.joeakeem.myconotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Instruction.
 */
@Entity
@Table(name = "instruction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "instruction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Instruction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "instruction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "observation", "strain", "instruction" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "instructions" }, allowSetters = true)
    private Tek instructionSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Instruction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Instruction title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Instruction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setInstruction(null));
        }
        if (images != null) {
            images.forEach(i -> i.setInstruction(this));
        }
        this.images = images;
    }

    public Instruction images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Instruction addImages(Image image) {
        this.images.add(image);
        image.setInstruction(this);
        return this;
    }

    public Instruction removeImages(Image image) {
        this.images.remove(image);
        image.setInstruction(null);
        return this;
    }

    public Tek getInstructionSet() {
        return this.instructionSet;
    }

    public void setInstructionSet(Tek tek) {
        this.instructionSet = tek;
    }

    public Instruction instructionSet(Tek tek) {
        this.setInstructionSet(tek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instruction)) {
            return false;
        }
        return id != null && id.equals(((Instruction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instruction{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
