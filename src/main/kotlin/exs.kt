abstract class Employee (
    val firstName: String,
    val secondName: String,
    val baseSalary: Double,
    val experience: Int,
    var manager: Manager ? = null
    ) {
    open fun calcSalary () : Double {
        var salary: Double
        if (experience > 5) {
            salary =  baseSalary*1.2 + 500
        } else if (experience > 2) {
            salary =  baseSalary + 200
        } else {
            salary =  baseSalary
        }
        return salary;
    }

    fun giveSalary () {
        println("$firstName $secondName got salary: ${ Math.round(calcSalary()).toDouble() }")
    }

    fun assignToManager(assignedManager : Manager) {
        manager = assignedManager
        assignedManager.team.add(this)
    }
}

class Developer (
    firstName: String,
    secondName: String,
    baseSalary: Double,
    experience: Int
) : Employee (firstName, secondName, baseSalary, experience)  {

}

class Designer (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var effCoeff : Double
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        return super.calcSalary() * effCoeff
    }
}



class Manager (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var team : MutableList<Employee> = mutableListOf()
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        var salary = super.calcSalary()
        val teamNum = team.count()
        val devNum = team.filterIsInstance<Developer>().count()
        if (teamNum > 10) {
            salary = salary + 300
        } else if (teamNum > 5) {
            salary = salary + 200
        }
        if (devNum > teamNum / 2) {
            salary = salary * 1.1
        }
        return salary;
    }

    fun addEmployee (employee : Employee) {
        team.add(employee)
        employee.manager = this
    }
}



class Department (
    var managers : MutableList<Manager> = mutableListOf()
) {

    fun addManager(manager: Manager) {
        managers.add(manager)
    }

    fun giveAllSalaries() {
        managers.forEach{
                manager ->
            manager.giveSalary()
            manager.team.forEach{
                employee ->  employee.giveSalary()
            }
        }
    }
}



fun main () {
    var Jon =  Developer("Jon","Dou",1400.00, 1);
    var Vasya =  Developer("Vasya", "Pupkin",1500.00, 2);
    var Petya =  Developer("Petya","Vasiechkin",1500.00, 3)
    var Dima =  Developer("Dima","Sergeev",1500.00, 7);
    var Vera =  Developer("Vera","Ivanova",1500.00, 5);
    var Pablo =  Developer("Pablo","Neizvesten",1400.00, 4);
    var Tanya =  Developer("Tanya","Petrova",1500.00, 4);

    var Mila =  Designer("Mila","Yovovich",1400.00, 2, 0.6);
    var Katia =  Designer("Katia","Petrova",1400.00, 1, 0.7);
    var Danya =  Designer("Danya","Molchanov",1400.00, 4, 0.8);
    var Daria =  Designer("Daria","Kovalenko",1400.00, 6, 0.9);

    var Borys =  Manager("Borys","Nachalnik",2000.00, 6);
    var Platon =  Manager("Platon","Nachalnik",2000.00, 4);

    Vasya.assignToManager(Borys)
    Petya.assignToManager(Borys)
    Daria.assignToManager(Borys)
    Katia.assignToManager(Borys)
    Dima.assignToManager(Borys)
    Jon.assignToManager(Borys)
    Vera.assignToManager(Platon)

    Pablo.assignToManager(Platon)
    Tanya.assignToManager(Platon)
    Danya.assignToManager(Platon)
    Mila.assignToManager(Platon)

    var production = Department()

    production.addManager(Borys)
    production.addManager(Platon)

    production.giveAllSalaries()

}