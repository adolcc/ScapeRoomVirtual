package exception.factory;

import constant.ElementType;
import constant.EntityType;
import constant.FieldName;
import exception.core.DuplicateResourceException;
import exception.core.NotFoundException;
import exception.core.ValidationException;

public class ExceptionFactory {

    public static ValidationException requiredField(FieldName fieldName) {
        return new ValidationException(String.format("El campo '%s' es obligatorio.", fieldName.getDisplayName()));
    }

    public static ValidationException invalidPrice() {
        return new ValidationException("El precio debe ser un valor positivo.");
    }

    public static ValidationException invalidEmail() {
        return new ValidationException("El formato del email no es válido.");
    }

    public static ValidationException insufficientElements(ElementType element, int minRequired) {
        return new ValidationException(String.format("Se requieren al menos %d %s", minRequired, element.getDisplayName()));
    }

    public static DuplicateResourceException duplicateValue(EntityType entity, String identifier) {
        return new DuplicateResourceException(String.format("Ya existe %s '%s'.", entity.getDisplayName(), identifier));
    }

    public static NotFoundException notFound(EntityType resourceType, String identifier) {
        return new NotFoundException(String.format("%s '%s' no encontrado.", resourceType.getDisplayName(), identifier));
    }
}
