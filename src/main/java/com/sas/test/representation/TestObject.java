/*
 * copyright(c) 2017 SAS Institute, Cary NC 27513
 * Created on Jul 13, 2017
 *
 */
package com.sas.test.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Objects;

/**
 * @author <A HREF="mailto:Gary.Williams@sas.com">Gary Williams</A>
 */
public class TestObject
{
    private String id;

    private String name;

    private String description;

    private Date creationTimeStamp;

    private Date modifiedTimeStamp;

    public TestObject()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getCreationTimeStamp()
    {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp)
    {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Date getModifiedTimeStamp()
    {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(Date modifiedTimeStamp)
    {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    @JsonIgnore
    public String getEtag()
    {
        return "\""+hashCode()+"\"";
    }

    @Override
    public int hashCode()
    {
        final int prime = 83;
        int result = 7;
        result = prime * result + super.hashCode();
        result = prime * result + Objects.hashCode(id);
        result = prime * result + Objects.hashCode(name);
        result = prime * result + Objects.hashCode(description);
        result = prime * result + Objects.hashCode(creationTimeStamp);
        result = prime * result + Objects.hashCode(modifiedTimeStamp);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        if(!super.equals(obj)) return false;
        TestObject rhs = (TestObject)obj;
        return (Objects.equals(id, rhs.id) &&
                Objects.equals(name, rhs.name) &&
                Objects.equals(description, rhs.description) &&
                Objects.equals(creationTimeStamp, rhs.creationTimeStamp) &&
                Objects.equals(modifiedTimeStamp, rhs.modifiedTimeStamp));
    }

    @Override
    public String toString()
    {
        return "TestObject(" + id + ") " + name + " - " + description;
    }
}
