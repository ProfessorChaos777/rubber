package com.example.rubberbridge

class Robber(var table: Table_to_draw=Table_to_draw()) {
    var games: MutableList<Game> = mutableListOf()

    fun addGame(game:Game){
        games.add(game)
        updatetable()
    }
    fun removeGame(){
        games.removeAt(games.size-1)
        updatetable()
    }

    //здесь будет обновляться таблица
    fun  updatetable(){
        table = Table_to_draw()
        var pointResult:PointResult
        for(game in games){
            pointResult=getPointResult(game,table.zoneTeam1,table.zoneTeam2)

            if(pointResult.winnerteam == 1){
                table.allPointsTeam1 += pointResult.allPoints
                table.partPointsTeam1 += pointResult.partPoints
                if(table.partPointsTeam1 >= 100){
                    if(table.zoneTeam1){
                        table.endGame = true
                        //геймовая премия
                        table.allPointsTeam1 += 500
                    }else{
                        table.partPointsTeam1 = 0
                        table.partPointsTeam2 = 0
                        table.zoneTeam1 = true
                        //геймовая премия
                        table.allPointsTeam1 += 200
                    }
                }
            }
            if(pointResult.winnerteam == 2){
                table.allPointsTeam2 += pointResult.allPoints
                table.partPointsTeam2 += pointResult.partPoints
                if(table.partPointsTeam2 >= 100){
                    if(table.zoneTeam2){
                        table.endGame = true
                        table.allPointsTeam2 += 500
                        //геймовая премия
                    }
                    else{
                        table.partPointsTeam2 = 0
                        table.partPointsTeam1 = 0
                        table.zoneTeam2 = true
                        //геймовая премия
                        table.allPointsTeam2 += 200
                    }
                }
            }
        }
    }


    //здесь преобразовывается результат игры в очки
    fun  getPointResult(game:Game,zoneTeam1:Boolean,zoneTeam2:Boolean):PointResult{
        var pointResult:PointResult=PointResult(0,0,0)
        val zoneGame:Boolean = (zoneTeam1 && game.team==1)||(zoneTeam2 && game.team==2)
        if(game.contract.dbl == 0){
            if(game.result >= game.contract.level + 6){
                when(game.contract.suit){
                    0-> pointResult.partPoints=game.contract.level*20
                    1-> pointResult.partPoints=game.contract.level*20
                    2-> pointResult.partPoints=game.contract.level*30
                    3-> pointResult.partPoints=game.contract.level*30
                    4-> pointResult.partPoints=game.contract.level*30+10
                }

                when(game.contract.suit){
                    0-> pointResult.allPoints=(game.result-6)*20
                    1-> pointResult.allPoints=(game.result-6)*20
                    2-> pointResult.allPoints=(game.result-6)*30
                    3-> pointResult.allPoints=(game.result-6)*30
                    4-> pointResult.allPoints=(game.result-6)*30+10
                }

                pointResult.winnerteam=game.team
            }
            else
            {
                pointResult.allPoints=(game.contract.level+6-game.result)*50
                if(zoneGame) pointResult.allPoints*=2

                when(game.team){
                    1-> pointResult.winnerteam=2
                    2-> pointResult.winnerteam=1
                }
            }
        }

        //шлемики
        if(game.result>=game.contract.level + 6)
        {
            pointResult.allPoints+=when(game.contract.level){
                6->if(zoneGame) 750 else 500
                7->if(zoneGame) 1500 else 1000
                else->0
            }
        }


        return pointResult
    }

}

class PointResult(var winnerteam:Int,var allPoints:Int,var partPoints:Int)

//result - количество взяток
class Game(var team:Int,var result:Int,var contract:Contract)

class Table_to_draw (var allPointsTeam1:Int=0,var partPointsTeam1:Int=0,var zoneTeam1:Boolean=false,var allPointsTeam2:Int=0,var partPointsTeam2:Int=0,var zoneTeam2:Boolean=false,var endGame:Boolean=false)

//suit: 0- миноры , 1-мажоры, 2- бк
//dbl 0- без контры, 1- с контрой , 2- с реконтрой
class Contract(val level:Int,val suit:Int,val dbl:Int)