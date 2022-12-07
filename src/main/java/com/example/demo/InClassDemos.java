package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@SpringBootApplication
@RequestMapping("/api")
public class InClassDemos
{
    static ConfigurableApplicationContext appCtx;
    
    static int lastI = 0;
    static HashMap<String, String> codes;
    private static final ArrayList<Person> names = new ArrayList<>();
    
    public static void main(String[] args)
    {
        createCodes();
        String str = null;
        
        try
        {
            func3();
            System.out.println("no exception");
        }
        catch (Exception e)
        {
            System.out.println("yup, exception");
			
			String tstr = e.toString();
			System.out.println("Exception happened: " + tstr);
			
			StackTraceElement[] st = e.getStackTrace();
			StackTraceElement st0 = st[0];
			System.out.println("st0 = " + st0.getLineNumber());
			
			System.out.println("end of stack trace");
        }
        
        
        String strNr = "b";
        
        int intNr;
        try
        {
            intNr = Integer.parseInt(strNr);
        }
        catch (NumberFormatException e)
        {
            intNr = -1;
            System.out.println("Exceeeeption");
        }
        
        
        System.out.println("the parsed number is " + intNr);
        
        
        ///////////////////////////////////
        ///////////////////////////////////
        
        
        try
        {
            System.out.println("trying something...");
            someFunc("something");
            System.out.println("something passed");
        }
        catch (MyException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        
        try
        {
            System.out.println("trying \"null\"...");
            someFunc("null");
            System.out.println("\"null\" passed");
        }
        catch (MyException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        
        
        ///////////////////////////////////
        ///////////////////////////////////
        
        try
        {
            System.out.println("always gonna print");
            str.equals("");
            System.out.println("gonna print only if NO exception");
        }
        catch (Exception e)
        {
            System.out.println("gonna print only if exception");
        }
        finally
        {
            System.out.println("always gonna print");
        }
        
		
		///////////////////////////////////
        
		
        appCtx = SpringApplication.run(InClassDemos.class, args);
    }
	
	static void func1() {
		String str = null;
		str.toString(); // gonna throw NullPointerException
	}
	static void func2() {
		func1();
	}
	static void func3() {
		func2();
	}
    
    static String someFunc(String arg) throws MyException
    {
        if (arg.equals("null"))
        {
            throw new MyException("Argument cannot have value \"null\"");
        }
        else
        {
            return "all good";
        }
    }
    
    static void createCodes()
    {
        codes = new HashMap<>();
        codes.put("alex", "aaa");
        codes.put("amina", "344");
        codes.put("bassam", "158");
        codes.put("frode", "75e");
        codes.put("lukas", "2h2");
        codes.put("mariem", "sd4");
        codes.put("matilda", "89q");
        codes.put("mohamed", "h23");
        codes.put("raju", "78f");
        codes.put("sahar", "sh8");
        codes.put("samyuktha", "23g");
        codes.put("zain", "9g6");
    }
    
    @GetMapping("/names")
    public Object getAllPersons(HttpServletRequest req)
    {
        boolean hasLN = "true".equals(req.getParameter("hasLastName"));
        boolean noLN = "false".equals(req.getParameter("hasLastName"));
        
        ArrayList<Person> retArr = new ArrayList<>();
        for (Person p : names)
        {
            if (hasLN)
            {
                if (p.lastName != null && !p.lastName.isEmpty())
                    retArr.add(p);
            }
            else if (noLN)
            {
                if (p.lastName == null || p.lastName.isEmpty())
                    retArr.add(p);
            }
            else
            {
                retArr.add(p);
            }
        }
        
        return retArr;
    }
    
    @GetMapping("/names/{id}")
    public Object getSpecificPerson(@PathVariable("id") int id)
    {
        for (Person p : names)
        {
            if (p.id == id)
            {
                return p;
            }
        }
        return "No person with that id";
    }
    
    @GetMapping("/names/{id}/firstName")
    public Object getPersonFirstName(@PathVariable("id") int id)
    {
        for (Person p : names)
        {
            if (p.id == id)
            {
                return p.firstName;
            }
        }
        return "No person with that id";
    }
    
    @GetMapping("/names/{id}/lastName")
    public Object getPersonLastName(@PathVariable("id") int id)
    {
        for (Person p : names)
        {
            if (p.id == id)
            {
                return p.lastName;
            }
        }
        return "No person with that id";
    }
    
    @PutMapping("/names/{id}/firstName")
    public Object setPersonFirstName(@PathVariable("id") int id,
                                     @RequestBody String body)
    {
        int iToUpdate = -1;
        Person myP = null;
        for (Person p : names)
        {
            if (p.id == id)
            {
                myP = p;
                iToUpdate = names.indexOf(p);
                break;
            }
        }
        
        if (myP == null)
            return "No person with that id";
        
        myP.firstName = body;
        names.set(iToUpdate, myP);
        return "";
    }

    @PutMapping("/names/{id}/lastName")
    public Object setPersonLastName(@PathVariable("id") int id,
                                    @RequestBody String body)
    {
        int iToUpdate = -1;
        Person myP = null;
        for (Person p : names)
        {
            if (p.id == id)
            {
                myP = p;
                iToUpdate = names.indexOf(p);
                break;
            }
        }
        
        if (myP == null)
            return "No person with that id";
        
        myP.lastName = body;
        names.set(iToUpdate, myP);
        return "";
    }
    
    @PostMapping("/names")
    public void createPerson(@RequestBody Person body)
    {
        Person person = new Person(lastI++, body.firstName, body.lastName);
        names.add(person);
    }
    
    @PutMapping("/names/{id}")
    public void updatePerson(@PathVariable("id") int id,
                             @RequestBody Person body)
    {
        String fn = null;
        String ln = null;
        int iToUpdate = -1;
        for (Person p : names)
        {
            if (p.id == id)
            {
                fn = body.firstName != null ? body.firstName : p.firstName;
                ln = body.lastName != null ? body.lastName : p.lastName;
                iToUpdate = names.indexOf(p);
                break;
            }
        }
        
        Person person = new Person(id, fn, ln);
        if (iToUpdate > -1)
            names.set(iToUpdate, person);
    }
    
    @DeleteMapping("/names/{id}")
    public void deletePerson(@PathVariable("id") int id)
    {
        int iToDel = -1;
        for (Person p : names)
        {
            if (p.id == id)
            {
                iToDel = names.indexOf(p);
                break;
            }
        }
        if (iToDel > -1)
            names.remove(iToDel);
    }
    
    @GetMapping("/square-number/{nr}")
    public String squareNumber(@PathVariable("nr") String strNr)
    {
        double nr;
        try
        {
            nr = Double.parseDouble(strNr);
        }
        catch (Exception e)
        {
            return "Couldn't parse the variable into a number";
        }
        System.out.println("nr = " + nr);
        return "" + nr * nr;
    }
    
    @CrossOrigin(origins = "http://localhost:3002")
    @GetMapping("/open-to-one")
    public String openToOne()
    {
        return "If you're on the same location as me, OR localhost:3002 (or 3001), you'll get this message";
    }
    
    @CrossOrigin
    @GetMapping("/open-to-all")
    public String openToAll()
    {
        return "This message is available to all requesters, regardless of ORIGIN";
    }
    
    @GetMapping("/names/{id}/code")
    public String getCode(@PathVariable("id") int id)
    {
        for (Person p : names)
        {
            if (p.id == id)
            {
                return getSecretCode(p);
            }
        }
        return "Nope";
    }
    
    private String getSecretCode(Person p)
    {
        if (p.firstName == null)
            return "null first name!";
        return codes.get(p.firstName.toLowerCase().trim());
    }
}
