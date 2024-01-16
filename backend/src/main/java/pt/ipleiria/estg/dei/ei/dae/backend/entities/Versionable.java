package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public class Versionable {
    // used for the managing concurrency: if version number doesn't match -> optimistic lock exception
    // https://stackoverflow.com/questions/2572566/java-jpa-version-annotation
    @Version
    private int version;
}
