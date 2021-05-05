This app makes use of the MVVM pattern and clean architecture.

The domain layer contains the models of the app
The data layer provides definitions for accessing data sources
The UI layer handles presentation

The frameworks I have used are those that I believe are the current standard for developing Android apps:
    Retrofit for REST web service requests and conversion to objects
    Hilt for dependency injection, (rather than Dagger) because I have already used it in the past and thought it would fulfill the needs of the app
    Coroutines for asynchronous calls

I believe one of the most difficult parts of this test was getting my head around the way to calculate the amount of issues over the past year by week, and displaying them in an appropriate way

I have included tests for the ViewModels as well as for the "repository" (which is named RepositoryRetriever in this app)
I started creating tests after the fact, so that can be one of the areas I could improve on: writing tests beforehand and only then developing the features, in order to implement TDD

As far as completion percentage goes, I would say the app is 95% done since, in my opinion, it does comply with the requirements, but the user experience and interface could be further polished for a more pleasant use of the application
