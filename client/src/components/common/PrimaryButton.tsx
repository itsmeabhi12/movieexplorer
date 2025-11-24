import { CircularProgressIndicator } from "./CircularProgressIndicator";

interface PrimaryButtonParam {
  title: string;
  type?: "button" | "submit" | "reset";
  onTap: () => void;
  isLoading: boolean;
}

export function PrimaryButton({
  title,
  onTap,
  isLoading,
  type = "button",
}: PrimaryButtonParam) {
  return (
    <button
      type={type}
      onClick={onTap}
      disabled={isLoading}
      className="bg-blue-600 text-white p-2 rounded font-medium hover:bg-blue-500 active:scale-[0.97] transition focus:outline-none focus:ring-2 focus:ring-blue-500"
    >
      {isLoading ? (
        <div className="flex justify-center items-center">
          <CircularProgressIndicator />
        </div>
      ) : (
        title
      )}
    </button>
  );
}
