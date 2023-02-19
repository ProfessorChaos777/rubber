package com.example.rubberbridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rubberbridge.databinding.FragmentResultOfDealBinding
import java.io.File
import java.io.PrintWriter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultOfDeal.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultOfDeal : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentResultOfDealBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_result_of_deal, container, false)
        _binding = FragmentResultOfDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun hasExternalStoragePrivateFile(): Boolean {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        val path = context?.getFilesDir()
        val letDirectory = File(path, "Rubber")
        letDirectory.mkdirs()

        val sd_main = File(letDirectory, "Results.txt")
        var success = true

        if (!sd_main.exists())
            success = sd_main.mkdir()

        if (success) {
            val sd = File("filename.txt")
            return sd.exists()
           // if (!sd.exists())
           //     success = sd.mkdir()
//
           // if (success) {
           //     // directory exists or already created
           //     val dest = File(sd, "Records.txt")
           //     try {
           //         // response is the data written to file
           //         PrintWriter(dest).use { out -> out.println("11") }
           //     } catch (e: Exception) {
           //         // handle the exception
           //     }
           // }
        } else {
            // directory creation is not successful
        }

        return sd_main.exists()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonApproveContract.setOnClickListener {
           //var text_length = binding.editTextPlayer1.length()
           if ( binding.editLevel.length() == 0 ||
                binding.editPlayer.length() == 0 ||
                binding.editResultLevel.length() == 0 ||
                binding.editSuit.length() == 0 ) {

               binding.errorText.setText(getString(R.string.not_enough_players))
           } else {
               findNavController().navigate(R.id.action_to_Table)
             // val path = context?.getExternalFilesDir(null)
             // val letDirectory = File(path, "Rubber")

             // var success = letDirectory.mkdirs()

             // if (success) {
             //     val sd = File(letDirectory, "Results.txt")
             //      if (!sd.exists())
             //          success = sd.mkdir()

             //      if (success) {
             //          // directory exists or already created
             //          val dest = File(sd, "Records.txt")
             //          try {
             //              // response is the data written to file
             //              var result = view.findViewById<TextView>(R.id.edit_level).text.toString() + " "
             //                           view.findViewById<TextView>(R.id.edit_suit).text.toString() + " "
             //                           view.findViewById<TextView>(R.id.edit_result_level).text.toString() + " "
             //                           view.findViewById<TextView>(R.id.edit_player).text.toString() + " "

             //              PrintWriter(dest).use { out -> out.println(result) }

             //              findNavController().navigate(R.id.action_to_Table)

             //          } catch (e: Exception) {
             //              // handle the exception
             //              binding.errorText.setText(getString(R.string.file_write_error))
             //          }
             //      }
             //     else {
             //          binding.errorText.setText(getString(R.string.file_write_error))
             //      }
             // } else {
             //     // directory creation is not successful

             //     binding.errorText.setText(getString(R.string.create_file_error))
             // }

           }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultOfDeal.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultOfDeal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}