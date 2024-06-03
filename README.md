The Groovy Git Accessor app is configured to create pull requests and merge created pull requests via the Git API.<br/>
<ul>
<li>The Input to the program is through the Input.txt file located within the workspace, Please configure the head and base branches the type of request etc in this file against respective keys</li> 
<li>Application Properties contains some re-usable text fragments and importantly the git token to use this app , Please add your token to the GIT_TOKEN key followed by the "token" string, ex: GIT_TOKEN= token alphanumeric git token </li>
<li>scheduler properties contains re-usable fragments to configure the scheduler to run pull or merge jobs periodically (Please set GIT_ACCESSOR_SCHEDULER_UNIT as only minute/minutes, hour/hours or day/days only; ex GIT_ACCESSOR_SCHEDULER_UNIT=minute) </li>
<li>Please only enter numeric value for key GIT_ACCESSOR_SCHEDULE_INTERVAL in scheduler.properties </li>
</ul>
