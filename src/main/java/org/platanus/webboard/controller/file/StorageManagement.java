package org.platanus.webboard.controller.file;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.platanus.webboard.controller.file.dto.StorageDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageManagement {

    private final PropertyEnvironment propertyEnvironment;

    public StorageDto getFullPath(StorageDto storage) {

//        storage.setFullPath();
        return null;
    }

}
