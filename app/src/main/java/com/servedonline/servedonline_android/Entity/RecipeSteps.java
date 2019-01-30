package com.servedonline.servedonline_android.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeSteps implements Parcelable {

    private int stepNumber;
    private String instruction;
    private int lastInstruction; //0 = false, 1 = true;

    public RecipeSteps(int stepNumber, String instruction, int lastInstruction) {
        this.stepNumber = stepNumber;
        this.instruction = instruction;
        this.lastInstruction = lastInstruction;
    }

    public RecipeSteps(Parcel in) {
        stepNumber = in.readInt();
        instruction = in.readString();
        lastInstruction = in.readInt();
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getInstruction() {
        return instruction;
    }

    public int isLastInstruction() {
        return lastInstruction;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeInt(stepNumber);
        out.writeString(instruction);
        out.writeInt(lastInstruction);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };
}
