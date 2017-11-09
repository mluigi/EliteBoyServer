package pr0.ves.eliteboy.server

import pr0.ves.eliteboy.server.services.CommanderService

interface Task {
    fun run(cmdr: Commander, service: CommanderService)
}