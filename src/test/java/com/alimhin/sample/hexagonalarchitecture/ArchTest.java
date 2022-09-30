package com.alimhin.sample.hexagonalarchitecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {

    private final static String BASE_PACKAGE = "com.alimhin.sample.hexagonalarchitecture";
    private final static String DOMAIN_PACKAGE = "com.alimhin.sample.hexagonalarchitecture.domain..";
    private final static String APPLICATION_PACKAGE = "com.alimhin.sample.hexagonalarchitecture.application..";
    private final static String INFRASTRUCTURE_PACKAGE = "com.alimhin.sample.hexagonalarchitecture.infrastructure..";
    private final static String SPRING_FRAMEWORK_PACKAGE = "org.springframework";

    @Test
    void domainAndApplicationShouldNotDependsOnInfrastructureLayer(){
        var importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);

        noClasses()
                .that()
                    .resideInAnyPackage(DOMAIN_PACKAGE)
                .or()
                    .resideInAnyPackage(APPLICATION_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(INFRASTRUCTURE_PACKAGE)
                .because("On hexagonal architecture, domain and application should not depends on infrastructure")
                .check(importedClasses);

    }

    @Test
    void domainAndApplicationShouldNotDependsOnAnyExternalFramework() {
        var importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);

        noClasses()
                .that()
                    .resideInAnyPackage(DOMAIN_PACKAGE)
                .or()
                    .resideInAnyPackage(APPLICATION_PACKAGE)
                .should().dependOnClassesThat()
                    .resideInAnyPackage(SPRING_FRAMEWORK_PACKAGE)
                .because("On hexagonal architecture, domain and application should not depends on spring framework")
                .check(importedClasses);
    }
}
