package com.example.rubberbridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rubberbridge.databinding.FragmentSetPlayersBinding
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetPlayers : Fragment() {

    private var _binding: FragmentSetPlayersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetPlayersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.buttonFirst.setOnClickListener {
            //var text_length = binding.editTextPlayer1.length()

            if( binding.editTextPlayer1.length() == 0 ||
                binding.editTextPlayer2.length() == 0 ||
                binding.editTextPlayer3.length() == 0 ||
                binding.editTextPlayer4.length() == 0 ) {

                binding.errorText.setText(getString(R.string.not_enough_players))
            }
            else {

                val letDirectory = File(context?.getFilesDir(), "Rubber")
                var success = true
                if(!letDirectory.exists())
                    success = letDirectory.mkdirs()

                if(!success) {
                    binding.errorText.setText(getString(R.string.create_directory_error))
                }
                else {
                    val sd2 = File(letDirectory, "Results_file.txt")

                    if (!sd2.exists()) {
                        success = sd2.createNewFile()
                        binding.errorText.setText(getString(R.string.create_file_error))
                    }
                    if (success) {
                        try {
                            var result = binding.editTextPlayer1.text.toString() + " "
                            result += binding.editTextPlayer2.text.toString() + " "
                            result += binding.editTextPlayer3.text.toString() + " "
                            result += binding.editTextPlayer4.text.toString() + " "

                            sd2.writeText(result)
                        } catch (e: Exception) {
                            // handle the exception
                            success = false
                            binding.errorText.setText(getString(R.string.open_file_error))
                        }
                    }

                    findNavController().navigate(R.id.action_SetPlayers_to_Table)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}