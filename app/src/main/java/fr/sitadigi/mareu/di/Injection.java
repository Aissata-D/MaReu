package fr.sitadigi.mareu.di;

import fr.sitadigi.mareu.service.ReunionApiServiceImplementation;
import fr.sitadigi.mareu.service.ReunionApiServiceInterface;

public class Injection {
    public  static ReunionApiServiceInterface service = new ReunionApiServiceImplementation();

    public static ReunionApiServiceInterface getService() {
        return service;
    }
}
