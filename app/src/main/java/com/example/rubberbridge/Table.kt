package com.example.rubberbridge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rubberbridge.databinding.FragmentSecondBinding
import java.io.File

//import com.example.rubberbridge.databinding.TableBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

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

class Table : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }
    fun tryGetResultsFromFile(view: View): String {
        val letDirectory = File(context?.getFilesDir(), "Rubber")
        var success = true
        if(!letDirectory.exists())
            success = letDirectory.mkdirs()

        val sd2 = File(letDirectory,"Results.txt")

        var result : List<String> = emptyList()

        if (!sd2.exists()) {
            success = sd2.createNewFile()
            binding.errorTextView.setText(getString(R.string.open_file_error))
        }
        if(success) {
            try {
                result = sd2.readLines()
            } catch (e: Exception) {
                // handle the exception
                success = false
                binding.errorTextView.setText(getString(R.string.create_directory_error))
            }
        }

        return result.get(0)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            val str = tryGetResultsFromFile(view)
            if(str.length > 1) {
                val level: Int = str[0].toInt()
                val suit: Int = str[2].toInt()
                val result: Int = str[4].toInt()
                val team: Int = str[6].toInt()

                val robber:Robber=Robber()

                robber.addGame(Game(team, result, Contract(level, suit,0)))

                binding.columnTopRight.setText("texts")

                binding.columnTopLeft.setText((robber.table.allPointsTeam1).toString())
                binding.columnTopRight.setText((robber.table.allPointsTeam2).toString())
            }
        }

        binding.buttonApproveContract.setOnClickListener {
            findNavController().navigate(R.id.action_Table_to_ResultOfDeal)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
