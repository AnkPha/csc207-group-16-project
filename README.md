# TasteMap - Restaurant Finder 
CSC207 Final Project

Matthew Chen, Golbarg Ghazinour, Ankur Phadke, Daniel Tan

In the individual part, you are asked to add code to the login use case to save the currently-logged-in
user by saving the user in the Data Access Layer. You are also asked to add a unit test for this.

In this lab, you will complete a logout use case as a telam. You will also begin to discuss your project
and the use cases that need to be implemented (in your blueprint).

We have created all the Clean Architecture classes necessary for the logout use case.

By next Thursday (July 10), your team will submit:
- your completed lab code [for credit]
- a draft of your project blueprint proposal. [required, but not for credit, it's mainly for the TA to give your team feedback]

_(recall, Phase 1 is your solo task of adding the storage of the currently-logged-in user)_. This group part can be done independently of the individual part.

## Task 0: Fork this repo on GitHub
**To get started, one team member should fork this repo on GitHub and share it with the team.
All of you should then clone it.**

* * *

Suggested logistics: One of you should invite the others to collaborate on their fork of the
original repo on GitHub. You can do this in your repo on GitHub under `Settings -> Collaborators`.
This will allow you to push branches to a common repo and then use pull requests to contribute
your code and review. To prevent others from pushing directly to the main branch,
we recommend you set branch protection rules on GitHub. Below are how the settings might look if you
add branch protection rules:

![image of branch protection rules for main with the
requirement of two approvers to merge in pull requests.
](images/branch_protection_rules.png)

You might also be asked whether to use branch ruleset or classic branch protection rules. Either can be used, but the classic branch protection rules is sufficient for this lab.
![image of ruleset v.s. classic protection rule.
](images/ruleset_or_classic.png)

* * *

To make sure you are ready to work on this group activity:

- Open the project in IntelliJ and make sure you can successfully run `app/Main.java`.
- Note: you may need to set the Project SDK in the `Project Structure...` menu, and possibly
also manually link the Maven project, as you did / will do in Phase 1.

## Task 1: Understanding the Program

You may notice that we have refactored the CA engine code _slightly_ since Phase 1, but the
way we build the engine is drastically different: we have switched from using Factories to
using the Builder design pattern, which we'll be discussing in lecture.

Open up `app.Main` and read it as a team.
- What are the Views and what are the current Use Cases?
- Which Uses Cases are triggered from each View?
- Which version of the DAO is `app.Main` using?

The major change since Phase 1 is that we have added the `app.AppBuilder` class which makes
it easier to understand how our CA engine is being constructed — it also makes `app.Main` nice and concise!
- Why do all those `addX` methods end in `return this;`?

Run the program and make sure the signup and login Use Cases work.

Currently, you'll notice that the "Log Out" button still doesn't actually log you out. It's time to fix
that button, which is part of the `LoggedInView`.
We have created all the classes for you, but some of the code is missing.
As a team, your task is to fill in the missing code so that the Logout Use Case is implemented.
**The next part of the readme describes how your team will do this.**

A special note about one buggy behaviour: If if you don’t fill out the username but click Sign Up button, you will be redirected to the Login view.
* * *

**Your team will know when you are done when:**

- Clicking the "Log Out" button takes the user back to the Login View when you use the program.
- The provided `LogoutInteractorTest` test passes.

The "Log Out" button is an instance variable in class `LoggedInVew`. Go find it.
Also look at the `interface_adapter.change_password.LoggedInViewModel`, which contains any
data showing on the `LoggedInVew`.

* * *

## Task 2: Dividing up the work

There are 12 `TODO` comments left in the files
Recall that you can use the TODO tool window to conveniently pull up a complete list.

Once the TODOs are all complete, the "Log Out" button _should_ work!

As a team, split up the TODOs (see below) between the members of your team.

There are TODOs in seven of the files.
Make sure each member has at least one TODO which they will be responsible for completing.
If your team prefers to work in pairs, that is fine too. Your individual branches
will not be graded for this — only the final, working version.

The TODOs are summarized below (by file) to help your team decide how to split them up:

* * *

- `Main.java`

    - [ ] TODO: add the Logout Use Case to the app using the appBuilder
    - Hint: check how the other usecases were added.

* * *

- `LoggedInView.java` (tip: refer to the other views for similar code)

    - [ ] TODO: save the logout controller in the instance variable.
    - [ ] TODO: execute the logout use case through the Controller

* * *

- `LogoutController.java` (tip: refer to the other controllers for similar code)

    - [ ] TODO: Save the interactor in the instance variable.
    - [ ] TODO: run the use case interactor for the logout use case

* * *

- `LogoutInputData.java` (should be done with the LogoutInteractor TODOs below)

    - [ ] TODO: save the current username in an instance variable and add a getter.

- `LogoutInteractor.java` (tip: refer to `ChangePasswordInteractor.java` for similar code)

    - [ ] TODO: save the DAO and Presenter in the instance variables.
    - [ ] TODO: implement the logic of the Logout Use Case

* * *

- `LogoutOutputData.java`

    - [ ] TODO: save the parameters in the instance variables.

* * *

- `LogoutPresenter.java` (tip: refer to `SignupPresenter.java` for similar code)

    - [ ] TODO: assign to the three instance variables.
    - [ ] TODO: have prepareSuccessView update the LoggedInState
    - [ ] TODO: have prepareSuccessView update the LoginState

* * *

## Branch and Pull Request (for credits)
_**Writing an effective pull request can benefit your team project development. Here is an optional reading: https://dev.to/mpermar/how-to-create-effective-pull-requests-2m8e. Overall speaking, make sure your pull request is clear and concise, and that it contains all the necessary information for your teammates to review your code. This will be part of homework 5 grading.**_

1. Make a branch named the first part of your UofT email address, everything before the `@`.

2. Complete your assigned TODOs and make a pull request on GitHub. In your pull request,
   briefly describe what your TODOs were and how you implemented them. If you aren't sure
   about part of it, include this in your pull request so everyone knows what to look
   for when reviewing — or you can of course discuss with your team before making your
   pull request since you are physically working in the same space.
    - **Important: don't push any changes to the `.idea` folder, as that
      may cause issues for your other teammates, as some files contain
      configurations specific to your individual IntelliJ projects.**

3. Review each other's pull requests to ensure each TODO is correctly implemented and that
   there are no Checkstyle issues in the files that were modified.

4. Once all TODOs are completed, your team should debug as needed to ensure the
   correctness of the code. Setting a breakpoint where the log-out use case
   interactor starts its work will likely be a great place to start when debugging.

And that's it; you now have a working Logout Use Case! Instructions for
how to submit your work on MarkUs will be posted later.

* * *
