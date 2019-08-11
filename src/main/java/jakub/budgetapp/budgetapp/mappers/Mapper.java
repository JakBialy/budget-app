package jakub.budgetapp.budgetapp.mappers;

public interface Mapper<F,T> {

    T toDto(F entity);

    F toEntity(T dto);
}
