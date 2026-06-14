# Quickstart: iptables-dragdrop

## Prerequisites

- Java 21+ installed on the host system.
- JavaFX runtime available for the target JVM distribution.
- A Linux target system for the generated `iptables` script.

## Run the application

1. Build and launch the desktop application.
   - If using Gradle: `./gradlew run`
   - If using a packaged JAR: `java -jar iptables-dragdrop.jar`

2. In the application UI, create a new rule using drag-and-drop.
   - Choose source/destination addresses
   - Select protocol and optional ports
   - Choose `ACCEPT`, `DROP`, or `REJECT`

3. Verify the preview pane shows the assembled rule correctly.

4. Export the script:
   - Copy the generated rules to clipboard, or
   - Save the generated rules to a `.txt` file.

5. Validate the exported file.
   - Open the saved `.txt` file and confirm it contains valid `iptables` filter commands.
   - Example rule line: `iptables -A INPUT -p tcp --dport 22 -j ACCEPT`

## Expected outcomes

- The UI remains responsive while editing rules.
- Filtering the preview updates immediately.
- The generated file preserves rule order and contains only `iptables` filter table commands.
- The exported text can be reused as a script on a Linux host with iptables.
