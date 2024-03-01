package com.animals.safety.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.animals.safety.R;
import com.animals.safety.data.Animal;
import com.animals.safety.data.AnimalData;
import com.animals.safety.databinding.FragmentDetailsBinding;
import com.google.android.material.snackbar.Snackbar;

public class DetailsFragment extends Fragment {

    public static final String KEY_ANIMAL = "ANIMAL";

    private FragmentDetailsBinding binding;

    private Animal getAnimal() {
        return (Animal) getArguments().getSerializable(KEY_ANIMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          //On remplit nos vues avec les détails via l'objet Animal retourné par la méthode getAnimal().
          binding.imageViewAvatar.setImageDrawable(AppCompatResources.getDrawable(getContext(), getAnimal().getBreed().getCover()));
          binding.textViewName.setText(getAnimal().getName());
          //La méthode getString(...) ici permet d'insérer notre valeur comme un 'parametre' dans une chaîne de caractère (noter %1$s dans la string).
          binding.textViewAge.setText(getString(R.string.value_age, String.valueOf(getAnimal().getAge())));
          binding.textViewWeight.setText(getString(R.string.value_weight, String.valueOf(getAnimal().getWeight())));
          binding.textViewHeight.setText(getString(R.string.value_height, String.valueOf(getAnimal().getHeight())));
  
        // Call de la méthode toFill() pour initialiser nos listeners
        toFill();

    }

    private void toFill() {
        //TODO : à completer

         // On assigne un click listener à notre bouton supprimé.
        //[avec une classe anonyme]
        binding.fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On pop un snack pour informer l'user de la suppression de l'animal.
                Snackbar.make(binding.getRoot(),getAnimal().getName()+" has been removed !",Snackbar.LENGTH_SHORT).show();
                //On récupère l'array des Animaux (animals) de notre instance AnimalData, sur laquelle on supprime l'animal actuel.
                AnimalData.animals.remove(getAnimal());
                //On quitte l'écran des 'details' en direction de l'écran 'home' via la méthode dédier.
                back();
            }
        });

        //On assigne un click listener à notre bouton Edition.
        // [avec une lambda]
        binding.fabEdit.setOnClickListener(view -> {
            //on navigue vers lecran de modification via la methode fournie.
            navigateToEdition();
        });
    }


    private void navigateToEdition() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailsFragment.KEY_ANIMAL, getAnimal());

        NavHostFragment.findNavController(DetailsFragment.this)
                .navigate(R.id.action_DetailsFragment_to_CreateFragment, bundle);
    }

    private void back() {
        NavHostFragment.findNavController(DetailsFragment.this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}