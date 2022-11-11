package Application.interfaces;

import java.util.List;

public interface ServicesInterface<T> {

    void updateServices(T nameClass);

    void deleteServices(T nameClass);

    T findIdServices(int id);

    List<T> findAllServices();
}
