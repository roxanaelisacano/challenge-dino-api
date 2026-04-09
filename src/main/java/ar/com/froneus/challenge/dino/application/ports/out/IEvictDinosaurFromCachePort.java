package ar.com.froneus.challenge.dino.application.ports.out;

public interface IEvictDinosaurFromCachePort {

    /** Elimina del caché la entrada individual del dinosaurio y todas las listas */
    void evict(Long id);

    /** Elimina del caché solo las listas (útil al crear un nuevo dinosaurio) */
    void evictLists();
}
