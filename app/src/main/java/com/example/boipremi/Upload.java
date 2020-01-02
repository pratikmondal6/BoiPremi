package com.example.boipremi;

class Upload {

    private String imageName;
    private String imageUri;

    public Upload(String imageName,String imageUri)
    {
        this.imageName=imageName;
        this.imageUri=imageUri;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void  setImageName(String imageName)
    {
        this.imageName=imageName;
    }
    public void setImageUri(String imageUri)
    {
        this.imageUri=imageUri;
    }
}
